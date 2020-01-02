(ns snake-re-frame.views
  (:require
   [re-frame.core :as re-frame]
   [snake-re-frame.subs :as subs]))

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])
        direction (re-frame/subscribe [::subs/direction])]
    [:div
     [:h1 "Hello from " @name]
     [:h2 "The direction: " @direction]]))
