[![Build status](https://ci.appveyor.com/api/projects/status/qnsao76ts5gqffbn?svg=true)](https://ci.appveyor.com/project/Sormat59/qa-diplom)
# Дипломная работа профессии "Тестировщик ПО"

### Данная работа представляет собой проект по автоматизированному тестированию web-приложения для покупки туристического тура.

Для запуска проекта необходим установленный на машину пользователя **[Docker](https://www.docker.com/)**.

По умолчанию проект настроен на подключение к MySQL.

### **Инструкция по запуску:**

1. Склонировать репозиторий

2. Запустить базы данных и мок банковского сервиса командой `docker-compose up -d`

3. Запустить целевое приложение консольной командой:
`java -jar artifacts/aqa-shop.jar`


### **Запуск тестов:**
- Запустить тесты командой
 ./gradlew clean test allureServe

Свертывание контейнеров Docker осуществляется командой

`docker-compose down`

### **Ссылки на документацию и отчеты:**
- [План тестирования](https://github.com/Sormat59/QA-Diplom/blob/master/documentation/Plan.md);
- [Отчет о проведенном тестировании](https://github.com/Sormat59/QA-Diplom/blob/master/documentation/Report.md);
- [Отчет об автоматизации тестирования](https://github.com/Sormat59/QA-Diplom/blob/master/documentation/Results.md).