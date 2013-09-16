(ns cljsrt.core
  (:require [goog.dom :as dom]))

(def canvas (.getElementById js/document "result"))
(def w (.-width canvas))
(def h (.-height canvas))

(def ctx (.getContext canvas "2d"))

(defn render []
  (.fillRect ctx 0 0 w h))

(defn main []
  (render)
  (.write js/document (str "Hello, ClojureScript!" w h)))

(main)

(comment
  ;; load cljs repl:
  (cemerick.piggieback/cljs-repl)
)
