(ns snake-re-frame.keyboard
  (:require
   [re-frame.core :as re-frame]
   [re-pressed.core :as rp]))

(defn setup
  []
  (re-frame/dispatch-sync [::rp/add-keyboard-event-listener "keyup"])
  (re-frame/dispatch [::rp/set-keyup-rules {:event-keys [[[:turn-direction "left"]
                                                          [{:keyCode 37}]]
                                                         [[:turn-direction "up"]
                                                          [{:keyCode 38}]]
                                                         [[:turn-direction "right"]
                                                          [{:keyCode 39}]]
                                                         [[:turn-direction "down"]
                                                          [{:keyCode 40}]]]}]))
