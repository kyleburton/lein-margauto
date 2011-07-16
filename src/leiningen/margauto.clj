(ns leiningen.margauto
  (:require
   leiningen.marg
   [noir.core :as noir]
   [noir.server :as server]))

(defn source-files-seq [dirs]
  (map str
       (filter
        #(and
          (.isFile %1)
          (.endsWith (str %1) ".clj"))
        (mapcat file-seq (map (fn [s] (java.io.File. s)) dirs)))))

(defn mtime [f]
  (.lastModified (java.io.File. f)))

(defn take-directory-snapshot [dirs]
  (apply
   str
   (map
    (fn [f]
      (format "%s:%s\n" f (mtime f)))
    (source-files-seq dirs))))

(noir/defpage "/" []
  (slurp "docs/uberdoc.html"))

(noir/defpage "/docs/uberdoc.html" []
  (slurp "docs/uberdoc.html"))

(defn margauto
  "Watch for changes and rebuild the documentation automatically.  Will also serve up documentation on http://localhost:3000/ to make it easier to view your documentation."
  [project & args]
  (leiningen.marg/marg project)
  (let [src-dirs   (get-in project [:margauto :src-dirs] ["src"])
        pause-time (get-in project [:margauto :sleep-time] 1000)
        jetty-port (get-in project [:margauto :port]       3000)
        server     (server/start jetty-port)]
    (loop [before (take-directory-snapshot src-dirs)
           after  before]
      (if-not (= after before)
        (leiningen.marg/marg project))
      (Thread/sleep pause-time)
      (recur after (take-directory-snapshot src-dirs)))))

