(ns cljsrt.rt
  (:require [clojure.browser.repl]
            [goog.dom :as dom]
            [goog.math :as math])
  (:use-macros [cljsrt.macros :only [forloop]]))

(def vec3 js/vec3)

;;================================================================================
;; Vector helpers
;;================================================================================
(defn get-x [v3] (aget v3 0))
(defn get-y [v3] (aget v3 1))
(defn get-z [v3] (aget v3 2))
(defn prn-vec3 [v3]
  (println "(" (get-x v3) (get-y v3) (get-z v3) ")"))

;;================================================================================
;; Define primitives.
;;================================================================================

(deftype Ray [origin dir])

(defprotocol Shapes
    (-intersect [ray])
    (-intersects? [ray]))

(deftype Spheres [_n _floats]
  Shapes
  (-intersect [ray]
    (let [[origin dir] ray]
      (gm/vec3. 0 0 0)))

  (-intersects? [ray]
    true))
;;================================================================================

(def canvas (dom/getElement "result"))
(def w (.-width canvas))
(def h (.-height canvas))
(def ctx (.getContext canvas "2d"))

(def aspect-ratio (/ w h))

(def fov (math/toRadians 60))

(def eye (.fromValues vec3 0 0 (- (/ aspect-ratio (* 2 (Math/tan (/ fov 2)))))))

(defn pixel-to-near-plane
  ([i j]
     "Clean, functional, pretty."
     (.fromValues vec3
                      (- (* 2 (/ i h)) aspect-ratio)
                      (- (* 2 (/ j h)) 1)
                      0))
  ([i j out_vec]
     "Avoid allocatig a new vector"
     (.set vec3 out_vec
           (- (* 2 (/ i h)) aspect-ratio)
           (- (* 2 (/ j h)) 1)
           0)))

(defn ray-from-pixel
  ([i j]
     "Clean implementation"
     (let [dir (.create vec3)
           _ (.subtract vec3 dir (pixel-to-near-plane i j) eye)
           _ (.normalize vec3 dir dir)]
       (Ray. eye dir)))
  ([i j out_dir out_near-vec]
     "Ugly but fast. out_dir and out_near-vec belong to a ray that is elsewhere"
     (let [_ (.subtract vec3 out_dir (pixel-to-near-plane i j out_near-vec) eye)
           _ (.normalize vec3 out_dir out_dir)])))

(defn render []
  (let [dir_vec (.create vec3)  ; Don't allocate a dir ray for every pixel
        near_vec (.create vec3)  ; Same for the vector at the near plane
        ray (Ray. eye dir_vec)
        image-data (.getImageData ctx 0 0 w h)
        bitmap (.-data image-data)
        ]
    (forloop [[j 0] (< j h) (inc j)]
             (forloop [[i 0] (< i w) (inc i)]
                      (let [index (* 4 (+ i (* j w)))
                            ;;ray (ray-from-pixel i j)]
                            _ (ray-from-pixel i j dir_vec near_vec)]
                        (aset bitmap (+ index 0) (* 200 (get-x (.-dir ray))))
                        (aset bitmap (+ index 1) (* 200 (get-y (.-dir ray))))
                        (aset bitmap (+ index 2) (* 200 (get-z (.-dir ray))))
                        (aset bitmap (+ index 3) 255)))
    (.putImageData ctx image-data 0 0))))

(defn main []
  (render))

(main)
(println "Hello, REPL!")
