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
        speed (re-frame/subscribe [::subs/speed])
        snake (re-frame/subscribe [::subs/snake])
        apple (re-frame/subscribe [::subs/apple])]
    [:div {:class (bg @starts)}
     [:h2 "Game score: " (count @snake)]
     [:div
      [:label "Max Size: "]
      [:input {:type "text"
               :style {:marginRight 3}
               :value @max-size
               :readOnly @starts
               :on-change #(re-frame/dispatch [:set-max-size (-> % .-target .-value)])}]]
     [:div
      [:label "Speed: "]
      [:input {:type "text"
               :style {:marginRight 3}
               :value @speed
               :readOnly @starts
               :on-change #(re-frame/dispatch [:set-speed (-> % .-target .-value)])}]]
     [:button {:on-click #(re-frame/dispatch [:start]) :disabled @starts} "Start"]
     [:button {:on-click #(re-frame/dispatch [:stop]) :disabled (not @starts)} "Stop"]
     [:button {:on-click #(re-frame/dispatch [:reset])} "Reset"]
     (render-board @max-size @snake @apple)]))
