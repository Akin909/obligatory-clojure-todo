(ns obligatory-todo.core
  (:require [reagent.core :as reagent :refer [atom]]))

(enable-console-print!)

(println "Obligatory Todo console")

;; define your app data so that it doesn't get over-written on reload

(def input-styles {:width "500px"
                   :height "40px"
                   :font-size "15px"
                   :border-radius "4px"
                   :border "1px solid grey"
                   :box-shadow "0 1px 0 rgba(0, 0, 0, 0.5)"
                   :margin "5px 0"
                   :padding "5px"})

(def button-styles {:font-size "20px"
                    :width "100px"
                    :height "50px"
                    :box-shadow "0 1px 0 rgba(0, 0, 0, 0.5)"
                    :margin "5px"
                    :border-radius "5px"
                    :border "none" :padding "5px"})

(defonce todos (atom (sorted-map)))
(defonce current-todo (atom 0))

(defn create-todo [value]
  (let [id ( swap! current-todo inc)]
  (swap! todos assoc {:id id :title value :completed false  })))

(defonce init (do
    (create-todo "Do a thing")
    ;(create-todo "Stuff is Happening")
    ))

(prn todos)
(defn todo-input
  "An input element which updates its value on change"
  [type value placeholder]
  [:input
   {:type type
    :style input-styles
    :placeholder placeholder
    :value @value
    :on-change #(reset! value (-> % .-target .-value ))
    ;:on-blur (if-not (empty? value) (create-todo @value) )
    }
   (prn todos)])

(defn save-button []
  [:button
   {:on-click ()
    :style button-styles}
   "Save"])

(defn add-todo [value]
  [:div {:style {:display "flex" :flex-direction "column"}}
   [todo-input "text" value "Add A Todo"]
   ])

(defn current-todos [items]
  (prn "items" items)
  [:ul
   (for [item items]
    ^{:key item} [:li "Todo: " item])
   ])

(defn todo-container []
  (let [title (atom nil)]
    (fn []
       [:center
       {:style {:font-family "Helvetica"}}
       [:h1
        "Obligatory Todo"]
       [add-todo title]
       (let [x (vals @todos)]
        (prn "vals" x)
        [current-todos x])
       ;[save-button]
       ])))

(reagent/render-component [todo-container]
                          (. js/document (getElementById "app")))

(defn on-js-reload []
(prn current-todo)
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)
