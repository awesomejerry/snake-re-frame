(ns snake-re-frame.core
  (:require
   [reagent.core :as reagent]
   [re-frame.core :as re-frame]
   [cljss.core :as css]
   [snake-re-frame.events :as events]
   [snake-re-frame.views :as views]
   [snake-re-frame.config :as config]
   [snake-re-frame.keyboard :as keyboard]))

(defn dev-setup []
  (when config/debug?
    (println "dev mode")))

(defn ^:dev/after-load mount-root []
  (re-frame/clear-subscription-cache!)
  (css/remove-styles!)
  (reagent/render [views/main-panel]
                  (.getElementById js/document "app")))

(defn init []
  (re-frame/dispatch-sync [::events/initialize-db])
  (dev-setup)
  (mount-root)
  (keyboard/setup))
