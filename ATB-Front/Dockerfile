# Étape 1 : Construire l'application Angular avec Node.js
FROM node:alpine AS build
WORKDIR /app

# Copier les fichiers package.json et package-lock.json
COPY package*.json ./

# Installer les dépendances
RUN npm install

# Exécuter ngcc
RUN npx ngcc --properties es2023 module main --first-only --create-ivy-entry-points

# Copier les autres fichiers du projet
COPY . .

# Construire l'application
RUN npm run build

# Étape 2 : Construire l'image finale avec Nginx
FROM nginx:stable

# Copier les fichiers construits depuis l'étape précédente vers le répertoire Nginx
COPY --from=build /app/dist/pfe-star-front/browser/ /usr/share/nginx/html

# Exposer le port 80
EXPOSE 80




# WORKDIR /usr/src/app

# COPY . /usr/src/app

# RUN npm install -g @angular/cli

# RUN npm install

# CMD ["ng", "serve", "--host", "0.0.0.0"]
