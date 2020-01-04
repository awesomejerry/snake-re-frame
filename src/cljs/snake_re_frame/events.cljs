(ns snake-re-frame.events
  (:require
   [re-frame.core :as re-frame]
   [snake-re-frame.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced defn-traced]]
   [district0x.re-frame.interval-fx]))

(def direction-map {"left" [-1 0] "up" [0 -1] "down" [0 1] "right" [1 0]})

(defn valid-turn?
  [current-turn next-turn]
  (every? #(not= % 0) (mapv + (get direction-map current-turn) (get direction-map next-turn))))

(defn hit-wall?
  [db]
  (some (fn [part] (some #(or (> % (dec (:max-size db))) (< % 0)) part)) (:snake db)))

(defn hit-body?
  [db]
  (let [[head & body] (:snake db)]
    (some #(= head %) body)))

(defn move-snake-head
  [direction-vector snake-part]
  (vector (+ (first snake-part) (first direction-vector)) (+ (second snake-part) (second direction-vector))))

(defn make-next-snake
  [db]
  (let [direction-vector (get direction-map (:direction db))
        snake (:snake db)]
    (if (not direction-vector)
      db
      (assoc db :snake (into [(move-snake-head direction-vector (first snake))] (pop snake))))))

(defn make-random-pos
  [db]
  [(rand-int (:max-size db)) (rand-int (:max-size db))])

(defn make-apple
  [db]
  (loop [pos (make-random-pos db)]
    (if (some #(= % pos) (:snake db))
      (recur (make-random-pos db))
      pos)))

(defn eat?
  [db]
  (= (:apple db) (first (:snake db))))

(defn check-apple
  [db]
  (let [should-make (not (:apple db))]
    (if should-make
      (assoc db :apple (make-apple db))
      (if (eat? db)
        (assoc db :snake (conj (:snake db) (last (:snake db))) :apple nil)
        db))))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/default-db))

(re-frame/reg-event-db
 :turn-direction
 (fn-traced [db [_ direction]]
            (if (valid-turn? (:direction db) direction)
              (assoc db :direction direction)
              db)))

(re-frame/reg-event-fx
 :start
 (fn-traced [{:keys [db]} _]
            {:dispatch-interval {:dispatch [:next-frame]
                                 :id :frame-interval
                                 :ms 500}
             :db (assoc db :starts true)}))

(re-frame/reg-event-fx
 :stop
 (fn-traced [{:keys [db]} _]
            {:clear-interval {:id :frame-interval}
             :db (assoc db :starts false)}))

(re-frame/reg-event-fx
 :next-frame
 (fn-traced [{:keys [db]} _]
            (let [new-db (make-next-snake (check-apple db))]
              (if (or (hit-wall? new-db) (hit-body? new-db))
                (do (re-frame/dispatch [:stop]) {:db db})
                {:db new-db}))))
