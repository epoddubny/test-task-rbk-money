# Тестовое задание

## Локальный запуск:

1) Создать директории для volume:
    ```bash
    mkdir -p /tmp/postgres
    mkdir -p /tmp/kafka
    ```

2) Установить переменные окружения с кредами для БД:
    ```bash
    export POSTGRES_USER=your login here
    export POSTGRES_PASSWORD=your password here
    export POSTGRES_DB=your db name here
    ```
3) Запустить приложение в docker:
    ```bash
    docker-compose up
    ```
4) Выполнить curl запрос (id в базе см в п.1 начальных условий):
    ```bash
    curl --location --request POST 'localhost:11213/send-transaction' \
    --header 'Content-Type: application/json' \
    --data-raw '{
        "transactionId": 123,
        "amount": 100.05,
        "data": "test"
    }'
    ```
5) Посмотреть логи приложения
## Начальные условия:

1) БД Postgres с одной таблицей transactions с полями id, amount, data. В таблице 4 записи.
Примерно вот так:
    ```sql
    create table transactions (
        id serial primary key,
        amount numeric(19,2),
        data json
    );
    insert into transactions (id, amount, data) values
    (123, 100.05, '{"a":1,"b":2}'::json),
    (124, 150.75, '{"a":10,"b":20}'::json),
    (125, 1010.00, '{"a":20,"b":30}'::json),
    (126, 15.5, '{"a":30,"b":40}'::json);
    ```
2) Из Кафки приходят сообщения с проведёнными транзакциями (по одному сообщению на транзакцию):
    ```json
    [
        {
          "PID": 123,
          "PAMOUNT": 94.7,
          "PDATA": 20160101120000
        },
        {   
          "PID": 123,
          "PAMOUNT": 94.7,
          "PDATA": 20160101120000
        },
        {   
          "PID": 124,
          "PAMOUNT": 150.75,
          "PDATA": 20160101120001
        },
        {   
          "PID": 125,
          "PAMOUNT": 1020.2,
          "PDATA": 20160101120002
        },
        {   
          "PID": 126,
          "PAMOUNT": 15.5,
          "PDATA": 20160101120003
        },
        {   
          "PID": 127,
          "PAMOUNT": 120.74,
          "PDATA": 20160101120004
        }
    ]
    ```

## Задача:
Нужно сверить транзакции из Кафки с транзакциями в БД. Сверить нужно сумму, искать нужно по id.
Нотификация о сверке должна записываться в другой топик в произвольной форме, но из сообщений должно быть понятно состояние транзакций.

## Требования:
* учесть, что в общем случае список транзакций можно будет получать не только из кафки
* форматов отчёта может быть несколько (реализовать нужно только один);
* способов нотификации может быть несколько (реализовать нужно только запись в другой топик);

## Ожидаемый результат:
* Исходники Spring Boot приложения на языке Java не ниже 11 версии, которые maven способен собрать в jar.
* Удобный способ проверить работу приложения
