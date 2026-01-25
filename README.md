# Topic 5: Fitness Club Membership & Class Booking

**Group:** IT-2515  
**Members:** Almansur Maxym, Kairat Bolatuly

### Project Domain
Система управления фитнес-клубом, предназначенная для администрирования абонементов, управления расписанием классов и учета бронирований.

### Core Entities (Milestone 1)
*   **Member:** Данные о клиенте клуба.
*   **Membership:** Информация о статусе и типе абонемента.
*   **FitnessClass:** Групповые занятия с ограничением по количеству мест.
*   **ClassBooking:** Записи клиентов на конкретные занятия.

### Architecture & SOLID
*   **Repository Pattern:** Доступ к данным реализован через интерфейсы (`MemberRepository`, `ClassRepository`, `BookingRepository`) для обеспечения независимости от конкретной БД.
*   **Service Layer:** Бизнес-логика вынесена в отдельные сервисы (`AdminService`, `BookingService`).
*   **Database:** Интеграция с PostgreSQL/Supabase через JDBC API.
*   **Exception Handling:** Реализованы кастомные исключения для обработки бизнес-ошибок (`MembershipExpiredException`, `ClassFullException`, `BookingAlreadyExistsException`).
*   **Design Patterns:**
    *   **Singleton:** `GymConfig` для централизованного управления конфигурацией.
    *   **Factory Method:** `MembershipFactory` для создания различных типов абонементов.
    *   **Builder:** `TrainingPlan.Builder` для гибкого создания планов тренировок.
    *   **Strategy (Lambda):** `NotificationService` для гибкой отправки уведомлений.

### Setup
Для запуска проекта необходимо создать файл `config.properties` в корневой директории со следующими полями (файл скрыт через `.gitignore` в целях безопасности):
```properties
db.url=jdbc:postgresql://your-host:port/postgres
db.user=your-username
db.password=your-password
```
Скомпилируйте и запустите `Main.java`.
