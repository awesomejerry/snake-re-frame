(ns snake-re-frame.keyboard
  (:require
   [re-frame.core :as re-frame]
   [re-pressed.core :as rp]))

(defn setup
  []
  (re-frame/dispatch-sync [::rp/add-keyboard-event-listener "keyup"])
  (re-frame/dispatch [::rp/set-keyup-rules {:event-keys [[[:move "left"]
                                                          [{:keyCode 37}]]
                                                         [[:move "up"]
                                                          [{:keyCode 38}]]
                                                         [[:move "right"]
                                                          [{:keyCode 39}]]
                                                         [[:move "down"]
                                                          [{:keyCode 40}]]]}]))
