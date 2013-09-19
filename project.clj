(defproject cljsrt "Physically Based Rendering in Clojurescript"
  :source-paths ["src/clj" "src/cljs"]
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-1847"]
                 [ring "1.2.0"]
                 [compojure "1.1.5"]
                 [enlive "1.1.1"]]

  :repl-options {:init-ns cljsrt.app}
  :plugins [[com.cemerick/austin "0.1.0"]
            [lein-cljsbuild "0.3.2"]
            [org.bodil/lein-noderepl "0.1.10"]]

  :cljsbuild {:builds [{:source-paths ["src/node"]
                        :compiler {:target :nodejs
                                   :output-to "nodeapp.js"
                                   :optimizations :simple
                                   :pretty-print true}}

                       {:source-paths ["src/cljs"]
                        :compiler {:output-to "target/classes/public/app.js"
                                   :foreign-libs [{:file "resources/gl-matrix-min.js"
                                                   :provides ["glmatrix"]}]
                                   :optimizations :simple
                                   :pretty-print true}}]})
