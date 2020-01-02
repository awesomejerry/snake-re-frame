(ns snake-re-frame.views
  (:require
   [re-frame.core :as re-frame]
   [cljss.core :refer-macros [defstyles]]
   [snake-re-frame.subs :as subs]))

(defstyles title []
  {:font-size "3rem"})

(defn main-panel []
  (let [name (re-frame/subscribe [::subs/name])
        direction (re-frame/subscribe [::subs/direction])]
    [:div
     [:h1 {:class (title)} "Hello from " @name]
     [:h2 "The direction: " @direction]]))
