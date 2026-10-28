# DotNet Packages

Мультиплатформенное приложение (Compose Multiplatform: Android, Desktop, Web) для обзора, поиска и исследования каталога .NET / NuGet пакетов, версий и зависимостей.

## Предметная область
Приложение решает задачу удобной навигации по библиотекам .NET:
- Просмотр каталога популярных .NET пакетов.
- Поиск по названию пакета и описанию.
- Экран «Ничего не найдено» с возможностью быстрого сброса поиска.
- Детальная страница пакета: текущие версии и список зависимых библиотек с возможностью перехода по графу зависимостей.

## Архитектура и стек технологий
- **Kotlin Multiplatform (KMP):** Общий модуль `:shared` с логикой и UI на Compose Multiplatform (Android, Desktop, Web).
- **Navigation 3 (JetBrains):** Использование `org.jetbrains.androidx.navigation3:navigation3-ui` с открытой типизацией роутов (`sealed interface Route`).
- **State & Data Handling:** MVI / MVVM архитектура с подпиской на `StateFlow`, без прямой передачи `ViewModel` в Composable-экраны.
- **Repository Pattern:** Использование `PackageRepository` и `MockPackageRepository` с локальными моками.

## Команды запуска

### Скрипты и проверки
- Проверка паритета строк между локалями:
  ```bash
  python3 tools/check-strings.py
  ```

### Android
- Сборка приложения:
  ```bash
  ./gradlew :app:assembleDebug
  ```
- Установка и запуск на эмуляторе/устройстве:
  ```bash
  ./gradlew :app:installDebug
  ```

### Desktop (JVM)
- Запуск десктопного приложения:
  ```bash
  ./gradlew :shared:run
  ```

### Web (WasmJS / JS)
- Запуск веб-версии:
  ```bash
  ./gradlew :shared:wasmJsBrowserDevelopmentRun
  ```
