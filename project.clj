(defproject rf-mat-dev "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.10.238"]
                 [reagent "0.7.0"]
                 [re-frame "0.10.5"]
                 [com.andrewmcveigh/cljs-time "0.5.2"]
                 [org.clojure/core.async "0.2.391"]
                 [re-com "2.1.0"]
                 [secretary "1.2.3"]
                 [compojure "1.6.1"]
                 [yogthos/config "0.8"]
                 [ring "1.6.3"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-defaults "0.3.2"]
                 [lein-heroku "0.5.3"]
                 [cljsjs/react "16.4.0-0"]
                 [cljsjs/react-dom "16.4.0-0"]
                 [cljs-react-material-ui "0.2.50"]
                 [day8.re-frame/http-fx "0.1.6"]                 ]

  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-ring "0.12.4"]
            [lein-npm "0.6.2"]]

  :min-lein-version "2.5.3"

  :source-paths ["src/clj"]

  :clean-targets ^{:protect false} ["resources/public/js/compiled" "target"
                                    "test/js"]

  :figwheel {:css-dirs ["resources/public/css"]
             :ring-handler rf-mat-dev.handler/dev-handler}

  :repl-options {:nrepl-middleware [cider.piggieback/wrap-cljs-repl]}
  :ring {:handler rf-mat-dev.handler/handler}
  :heroku {:app-name "rf-mat-dev"}
  :aliases {"dev" ["do" "clean"
                        ["pdo" ["figwheel" "dev"]]]
            "build" ["with-profile" "+prod,-dev" "do"
                          ["clean"]
                          ["cljsbuild" "once" "min"]]}

  :profiles
  {:dev
   {:dependencies [[binaryage/devtools "0.9.10"]
                   [day8.re-frame/re-frame-10x "0.3.3"]
                   [day8.re-frame/tracing "0.5.1"]
                   [figwheel-sidecar "0.5.16"]
                   [cider/piggieback "0.3.5"]
                   [re-frisk "0.5.3"]]

    :plugins      [[lein-figwheel "0.5.16"]
                   [lein-doo "0.1.8"]
                   [lein-pdo "0.1.1"]]
    :uberjar {:main rf-mat-dev.server, :aot :all}}
   :prod { :dependencies [[day8.re-frame/tracing-stubs "0.5.1"]]}}

  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/cljs"]
     :figwheel     {:on-jsload "rf-mat-dev.core/mount-root"}
     :compiler     {:main                 rf-mat-dev.core
                    :output-to            "resources/public/js/compiled/app.js"
                    :output-dir           "resources/public/js/compiled/out"
                    :asset-path           "js/compiled/out"
                    :source-map-timestamp true
                    :preloads             [devtools.preload
                                           day8.re-frame-10x.preload
                                           re-frisk.preload]
                    :closure-defines      {"re_frame.trace.trace_enabled_QMARK_" true
                                           "day8.re_frame.tracing.trace_enabled_QMARK_" true}
                    :external-config      {:devtools/config {:features-to-install :all}}
                    }}

    {:id           "min"
     :source-paths ["src/cljs"]
     :jar true
     :compiler     {:main            rf-mat-dev.core
                    :output-to       "resources/public/js/compiled/app.js"
                    :optimizations   :advanced
                    :closure-defines {goog.DEBUG false}
                    :pretty-print    false}}

    {:id           "test"
     :source-paths ["src/cljs" "test/cljs"]
     :compiler     {:main          rf-mat-dev.runner
                    :output-to     "resources/public/js/compiled/test.js"
                    :output-dir    "resources/public/js/compiled/test/out"
                    :optimizations :none}}
    ]}

  :main rf-mat-dev.server

  :aot [rf-mat-dev.server]

  :uberjar-name "rf-mat-dev.jar"

  :prep-tasks [["cljsbuild" "once" "min"] "compile"]
  )
