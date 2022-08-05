# Дипломный проект профессии «Тестировщик»

Дипломный проект представляет собой автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

## Документы
* [Задание](https://github.com/Gabr12el/QA_DIPLOM/blob/master/docs/Zadanie.md)
* [План автоматизации](https://github.com/Gabr12el/QA_DIPLOM/blob/master/docs/Plan.md)
                      
Дипломный проект представляет собой автоматизацию тестирования комплексного сервиса, взаимодействующего с СУБД и API Банка.

На локальном компьютере заранее должны быть установлены IntelliJ IDEA и Docker

## Процедура запуска авто-тестов:

**1.** Склонировать на локальный репозиторий [Дипломный проект](https://github.com/netology-code/qa-diploma) и открыть его в приложении IntelliJ IDEA.

**2.** Открыть Docker;

**3.** В терминале развернуть контейнер с помощью команды

    docker-compose up -d;

**4.** В новом терминале запустить целевое приложение:

     для mySQL - java "-Dspring.datasource.url=jdbc:mysql://localhost:3306/app" -jar artifacts/aqa-shop.jar 

     для postgresgl - java "-Dspring.datasource.url=jdbc:postgresql://localhost:5432/app" -jar artifacts/aqa-shop.jar

**5.** Открыть браузер и пройти по [ссылке](http://localhost:8080/);

**6.** В новом терминале запустить тесты

    для mySQL - ./gradlew test -Durl=jdbc:mysql://localhost:3306/app -Duser=app -Dpassword=pass ;

    для postgresgl - ./gradlew test -Durl=jdbc:postgresql://localhost:5432/app -Duser=app -Dpassword=pass ;

**7.** В новом терминале сформировать отчет командой

    ./gradlew allureServe .

**8.** Для завершения работы allureServe выполнить команду:

    Ctrl+C

**9.** Для остановки работы контейнеров выполнить команду:

    docker-compose down

### Дополнительные материалы:
[Руководство по оформлению Markdown файлов](https://gist.github.com/Jekins/2bf2d0638163f1294637#Emphasis)