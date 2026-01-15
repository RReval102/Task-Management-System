# Task Management System — QA Automation Framework (Java)

Многоуровневый учебный проект для демонстрации **автоматизации тестирования SPA‑приложения**:

- **UI**: Selenide + Page Object
- **API**: Rest‑Assured (+ встроенный тестовый REST‑сервер на Javalin)
- **DB**: Hibernate ORM + H2 (in‑memory)
- **Отчёты**: Allure
- **CI/CD**: GitHub Actions + публикация Allure отчёта на GitHub Pages

> Важно: прикладная часть здесь преднамеренно минимальная. Цель репозитория — показать подход к автотестам (архитектура, слои, запуск в CI), а не построить production‑систему.

---

## Оглавление

- [1. Что внутри](#1-что-внутри)
- [2. Стек](#2-стек)
- [3. Быстрый старт](#3-быстрый-старт)
- [4. Запуск тестов](#4-запуск-тестов)
- [5. Allure отчёт](#5-allure-отчёт)
- [6. CI/CD (GitHub Actions + Pages)](#6-cicd-github-actions--pages)
- [7. API контракты](#7-api-контракты)
- [8. Структура проекта](#8-структура-проекта)
- [9. Траблшутинг](#9-траблшутинг)
- [10. Идеи для развития](#10-идеи-для-развития)

---

## 1. Что внутри

Проект состоит из трёх независимых уровней проверок:

### UI (Selenide)

- Демонстрационная HTML‑страница: `src/test/resources/tasks.html`
- Page Object: `src/test/java/.../ui/pages/TasksPage.java`
- Тест: `src/test/java/.../ui/TaskUITest.java`

Тест открывает локальную страницу, добавляет задачу через форму и проверяет, что она появилась в списке.

### API (Rest‑Assured)

- Встроенный тестовый REST‑сервер: `src/main/java/.../api/TaskApiServer.java`
- Тест: `src/test/java/.../api/TaskApiTest.java`

Сервер поднимается внутри тестов (порт по умолчанию `7001`), после чего тест создаёт задачу и проверяет получение списка.

### DB (Hibernate)

- Сущность: `src/main/java/.../model/Task.java`
- DAO (CRUD): `src/main/java/.../dao/TaskDAO.java`
- Конфиг Hibernate: `src/main/resources/hibernate.cfg.xml` (H2 in‑memory)
- Тест: `src/test/java/.../db/TaskDbTest.java`

---

## 2. Стек

- Java 17
- Gradle
- JUnit 5
- Selenide (+ WebDriverManager)
- Rest‑Assured
- Hibernate ORM (Jakarta Persistence)
- H2 Database
- Javalin + Gson
- Allure

---

## 3. Быстрый старт

### Требования

- **JDK 17+**
- **Gradle 8+** (в CI Gradle ставится автоматически, локально — любым удобным способом)

### Установка

```bash
git clone <your-repo-url>
cd task-management-system
```

---

## 4. Запуск тестов

Запустить все тесты:

```bash
gradle clean test
```

Запустить только UI/API/DB (пример через фильтр классов):

```bash
gradle test --tests "*TaskUITest"
gradle test --tests "*TaskApiTest"
gradle test --tests "*TaskDbTest"
```

Примечание по UI: тесты запускаются **в headless‑режиме** (см. `TaskUITest`).

---

## 5. Allure отчёт

Сгенерировать отчёт локально:

```bash
gradle allureReport
```

Где лежит отчёт:

```
build/reports/allure-report/index.html
```

Результаты выполнения тестов (сырьё для отчёта):

```
build/allure-results
```

---

## 6. CI/CD (GitHub Actions + Pages)

Workflow: `.github/workflows/ci.yml`

Что делает пайплайн:

1. Checkout
2. Установка JDK 17
3. Установка Gradle
4. `gradle clean test`
5. `gradle allureReport`
6. Upload Allure отчёта как artifact
7. **Публикация Allure отчёта на GitHub Pages** (в ветку `gh-pages` при пуше в `main`)

### Как открыть отчёт на GitHub Pages

После первого успешного прогона:

1. В репозитории откройте **Settings → Pages**
2. В качестве Source выберите ветку **`gh-pages`**
3. Сохраните

Далее отчёт будет доступен по адресу:

```
https://<username>.github.io/<repo>/
```

---

## 7. API контракты

Тестовый сервер поднимает следующие эндпоинты:

- `GET /tasks` — вернуть список задач
- `POST /tasks` — создать задачу
- `GET /tasks/{id}` — получить задачу по id

Пример тела для `POST /tasks`:

```json
{
  "title": "API Task",
  "description": "API desc",
  "status": "NEW"
}
```

---

## 8. Структура проекта

```text
task-management-system/
├── build.gradle
├── settings.gradle
├── src/
│   ├── main/
│   │   ├── java/com/example/taskmanagement/
│   │   │   ├── api/TaskApiServer.java
│   │   │   ├── dao/TaskDAO.java
│   │   │   ├── model/Task.java
│   │   │   └── utils/HibernateUtil.java
│   │   └── resources/hibernate.cfg.xml
│   └── test/
│       ├── java/com/example/taskmanagement/
│       │   ├── api/TaskApiTest.java
│       │   ├── db/TaskDbTest.java
│       │   └── ui/
│       │       ├── TaskUITest.java
│       │       └── pages/TasksPage.java
│       └── resources/tasks.html
└── .github/workflows/ci.yml
```

---

## 9. Траблшутинг

### UI тесты не стартуют (WebDriver/Chrome)

- Убедитесь, что установлен Chrome/Chromium.
- Если CI среда отличается, проверьте `Configuration.headless = true`.

### Порт занят

API тесты используют порт `7001`. Если занят — измените порт в `TaskApiTest`.

### DB поведение

База — H2 in‑memory, живёт в рамках JVM процесса (`DB_CLOSE_DELAY=-1`).
Если нужно полностью изолировать тесты — добавьте очистку таблиц между тестами.

---

## 10. Идеи для развития

- Добавить полноценный UI (например, React SPA) и тесты на авторизацию/фильтры/пагинацию.
- Вынести конфигурацию (baseUrl, browser, headless) в properties/env.
- Добавить тестовые данные через Hibernate fixtures/seed.
- Подключить Testcontainers (PostgreSQL вместо H2).
- Добавить уровни (smoke/regression) через теги JUnit.
