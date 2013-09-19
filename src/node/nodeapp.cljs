(ns cljsrt.app)

(def express (js/require "express"))
(def app (express))

(.use app (.static express (+ __dirname "/target/classes/public")))

(defn -main [& args]
  (println "hello"))

(set! *main-cli-fn* -main)
