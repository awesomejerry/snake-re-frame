(ns snake-re-frame.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::starts
 (fn [db]
   (:starts db)))

(re-frame/reg-sub
 ::max-size
 (fn [db]
   (:max-size db)))

(re-frame/reg-sub
 ::apple
 (fn [db]
   (:apple db)))

(re-frame/reg-sub
 ::snake
 (fn [db]
   (:snake db)))
