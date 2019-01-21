(ns set-wallpaper.core
  (:gen-class)
  (:require [set-wallpaper.file-operations :as fo]
            [net.cgrand.enlive-html :as html]
            [org.httpkit.client :as http]
            [clojure.string :as str]))

(defn get-dom
  "Get HTML content for the given site"
  []
  (let [url "https://old.reddit.com/r/wallpaper/"]
    (html/html-snippet
     (:body @(http/get url {:insecure? true})))))

(defn get-file-extension
  "Gets the file extension from the image url"
  [image-url]
  (last (str/split image-url #"\.")))

(defn check-and-create-directory
  "
   Check for the folder and then creates one if not present.
   Return the folder path
  "
  [folder]
  (let [ exists? (fo/folder-exists folder)
        folder-path (fo/get-directory folder)]
    (if (not exists?)
      (do
        (fo/create-directory folder-path)
        folder-path)
      folder-path)))

(defn get-image-and-set-background
  "Fetches the image from the url and sets the background"
  [folder]
  (let [dom (get-dom)
        table-data (html/select dom [:div.sitetable])
        img-url (get-in (first (:content (first table-data))) [:attrs :data-url])
        file-extension (get-file-extension img-url)
        file-name (fo/get-file-name file-extension)
        verified-folder-path (check-and-create-directory folder)
        full-path (fo/get-file-full-path verified-folder-path file-name)]
    (fo/save-image-to-file img-url full-path)
    (fo/set-background full-path)))

(defn set-background
  "Set image background"
  ([folder]
   (get-image-and-set-background folder))
  ([]
   (let [folder "Pictures/reddit-wallpapers"]
     (get-image-and-set-background folder))))

(defn -main
  "Set wallpaper"
  [& args]
  (if (> (count args) 0)
  (set-background (first args))
  (set-background)))
