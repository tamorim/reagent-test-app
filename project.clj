(defproject reagent-test-app "0.1.0-SNAPSHOT"
  :description "Playing around with ClojureScript + Reagent"
  :url "http://github.com/tamorim/reagent-test-app"
  :license {:name "MIT"}

  :min-lein-version "2.5.3"

  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/clojurescript "1.7.170"]
                 [org.clojure/core.async "0.2.374"
                  :exclusions [org.clojure/tools.reader]]
                 [reagent "0.5.1"]]

  :plugins [[lein-figwheel "0.5.0-6"]
            [lein-cljsbuild "1.1.2" :exclusions [[org.clojure/clojure]]]]

  :source-paths ["src"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"]

  :cljsbuild {:builds
              [{:id "dev"
                :source-paths ["src"]

                ;; If no code is to be run, set :figwheel true for continued automagical reloading
                :figwheel {:on-jsload "reagent-test-app.core/on-js-reload"}

                :compiler {:main reagent-test-app.core
                           :asset-path "js/compiled/out"
                           :output-to "resources/public/js/compiled/reagent_test_app.js"
                           :output-dir "resources/public/js/compiled/out"
                           :source-map-timestamp true}}
               ;; This next build is an compressed minified build for
               ;; production. You can build this with:
               ;; lein cljsbuild once min
               {:id "min"
                :source-paths ["src"]
                :compiler {:output-to "resources/public/js/compiled/reagent_test_app.js"
                           :main reagent-test-app.core
                           :optimizations :advanced
                           :pretty-print false}}]}

  :figwheel {;; watch and update CSS
             :css-dirs ["resources/public/css"]})
