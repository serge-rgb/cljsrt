(ns cljsrt.core
  (:require [goog.dom :as dom]
            [goog.math :as math])
  (:use-macros [cljsrt.macros :only [forloop]]))

(def canvas (dom/getElement "result"))
(def w (.-width canvas))
(def h (.-height canvas))
(def ctx (.getContext canvas "2d"))

(defn render []
  ;; (.fillRect ctx 0 0 w h)
  (let [image-data (.getImageData ctx 0 0 w h)
        bitmap (.-data image-data)]
    (forloop [[j 0] (< j h) (inc j)]
             (forloop [[i 0] (< i w) (inc i)]
                      (let [index (* 4 (+ i (* j w)))]
                        (aset bitmap (+ index 0) (math/randomInt 255))
                        (aset bitmap (+ index 1) (math/randomInt 255))
                        (aset bitmap (+ index 2) (math/randomInt 255))
                        (aset bitmap (+ index 3) 255))))
    (.putImageData ctx image-data 0 0)))

(defn main []
  (render))

(main)

(comment
  ;; load cljs repl:
  (cemerick.piggieback/cljs-repl)
)
