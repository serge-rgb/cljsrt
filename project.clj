(defproject cljsrt "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :plugins [[lein-cljsbuild "0.3.3"]]

  :cljsbuild {
              :repl-listen-port 1337
              :repl-launch-commands {"rt-launch"
                                     ["firefox" "-jconsole" "file:///C:/Users/sglez/work/cljsrt/index.html"]}
              :builds [{:source-paths ["src"]
                        :compiler {
                                   :output-to "public/cljsrt.js"
                                   :optimizations :whitespace  ; whitespace, simple, advanced
                                   :pretty-print true
                                   :foreign-libs []
                                   }}]
               }

  :dependencies [[org.clojure/clojure "1.5.1"]
                 [org.clojure/clojurescript "0.0-1889"]
                 [com.cemerick/piggieback "0.1.0"]]

  :hooks [leiningen.cljsbuild]

  :repl-options {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]})
