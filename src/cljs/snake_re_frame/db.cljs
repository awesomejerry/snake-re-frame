(ns snake-re-frame.db)

(def default-snake
  [[2 0] [1 0] [0 0]])

(def default-db
  {:starts false
   :max-size 10
   :speed 500
   :snake default-snake
   :apple nil
   :direction "right"})
