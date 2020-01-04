(ns snake-re-frame.views
  (:require
   [clojure.string :as string]
   [re-frame.core :as re-frame]
   [cljss.core :refer-macros [defstyles]]
   [snake-re-frame.subs :as subs]))

(defstyles bg [starts]
  {:filter (str "grayscale(" (if starts "0" "1") ")")})

(defstyles row []
  {:height "30px"})

(defstyles cell [status]
  {:background-color status
   :display "inline-block"
   :width "30px"
   :height "30px"
   :border "1px solid black"
   :box-sizing "border-box"})

(defstyles title []
  {:font-size "3rem"})

(defn check-cell-status
  [snake apple pos]
  (if (= pos (first snake))
    "green"
    (if (some #(= pos %) snake)
      "chartreuse"
      (if (= apple pos)
        "red"
        "aliceblue"))))

(defn render-board
  [max-size snake apple]
  [:div
   (for [i (range 0 max-size)]
     ^{:key i} [:div {:class (row)}
                (for [j (range 0 max-size)]
                  ^{:key j} [:div {:class (cell (check-cell-status snake apple [j i]))}])])])

(defn main-panel []
  (let [starts (re-frame/subscribe [::subs/starts])
        max-size (re-frame/subscribe [::subs/max-size])
        snake (re-frame/subscribe [::subs/snake])
        apple (re-frame/subscribe [::subs/apple])]
    [:div {:class (bg @starts)}
     [:h2 "Game score: " (count @snake)]
     [:button {:on-click #(re-frame/dispatch [:start]) :disabled @starts} "Start"]
     [:button {:on-click #(re-frame/dispatch [:stop]) :disabled (not @starts)} "Stop"]
     (render-board @max-size @snake @apple)]))
