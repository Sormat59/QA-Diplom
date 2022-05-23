FROM node:current-alpine
WORKDIR /opt/app
COPY . .
RUN npm install --package-lock-only
CMD ["npm", "start"]
EXPOSE 9999