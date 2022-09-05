# Дипломный проект профессии «Тестировщик»

Дипломный проект представляет собой автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

## Документы
* [Задание](https://github.com/Gabr12el/QA_DIPLOM/blob/master/docs/Zadanie.md)
* [План автоматизации](https://github.com/Gabr12el/QA_DIPLOM/blob/master/docs/Plan.md)
* [Отчет по итогам тестирования](https://github.com/Gabr12el/QA_DIPLOM/blob/master/docs/Report.md)
* [Отчет по итогам автоматизированного тестирования](https://github.com/Gabr12el/QA_DIPLOM/blob/master/docs/Summary.md)

Дипломный проект представляет собой автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

На локальном компьютере заранее должны быть установлены IntelliJ IDEA и Docker

## Процедура запуска авто-тестов:

**1.** Склонировать на локальный репозиторий [Дипломный проект](https://github.com/netology-code/qa-diploma) и открыть его в приложении IntelliJ IDEA

**2.** Запустить Docker Desktop

**3.** Открыть проект в IntelliJ IDEA

**4.** В терминале запустить контейнеры:

    docker-compose up -d

**5.** Запустить целевое приложение:

     для mySQL: 
    java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar

     для postgresgl:
     java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar

**6.** Открыть второй терминал

**7.** Во втором терминале запустить тесты:

    для mySQL:
    ./gradlew clean test "-Ddb.url=jdbc:mysql://localhost:3306/app"

    для postgresgl: 
    ./gradlew clean test "-Ddb.url=jdbc:postgresql://localhost:5432/app"

**8.** Создать отчёт Allure и открыть в браузере:

    ./gradlew allureServe

**9.** Для завершения работы allureServe выполнить команду:

    Ctrl+C

**10.** Для остановки работы контейнеров выполнить команду:

    docker-compose down

## Работа с данными

### Просмотр данных в таблицах

- Для MySQL:

```select-mysql-table.cmd```

- Для PostgreSQL:

```select-postgres-table.cmd```

### Удаление всех данных из таблиц

- Для MySQL:

```clear-mysql-table.cmd```

- Для PostgreSQL:

```clear-postgres-table.cmd```

### Дополнительные материалы:
[Руководство по оформлению Markdown файлов](https://gist.github.com/Jekins/2bf2d0638163f1294637#Emphasis)