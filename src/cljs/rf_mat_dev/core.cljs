(ns rf-mat-dev.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [rf-mat-dev.events :as events]
   [rf-mat-dev.routes :as routes]
   [rf-mat-dev.views :as views]
   [rf-mat-dev.config :as config]
   ))


(defn dev-setup []
  (when config/debug?
    (enable-console-print!)
    (println "dev mode")))

(defn mount-root []
  (re-frame/clear-subscription-cache!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn ^:export init []
  (routes/app-routes)
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root))
