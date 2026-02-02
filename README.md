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

### Milestone 2 (Design Patterns & Language Features)
В рамках Milestone 2 были добавлены следующие возможности:
*   **Generic Repository:** Реализован интерфейс `Repository<T>` для стандартизации доступа к данным различных сущностей. `MemberRepository` и `ClassRepository` теперь расширяют этот общий интерфейс.
*   **Advanced Factory:** `MembershipFactory` был расширен для поддержки `VisitBasedMembership` (на 10 посещений), в дополнение к ежемесячным и годовым типам.
*   **Lambdas & Functional Interfaces:**
    *   Используются в `AdminService.extendMemberships` для фильтрации участников, которым требуется продление.
    *   `GenericFilter<T>` использует `java.util.function.Predicate` для фильтрации списков сущностей.
    *   `NotificationService` является функциональным интерфейсом, используемым как стратегия для отправки уведомлений.
*   **Singleton Pattern:** `GymConfig` обеспечивает централизованную конфигурацию (налоговые ставки, пароли, URL-адреса БД) во всем приложении.
*   **Builder Pattern:** `TrainingPlan` использует статический вложенный класс `Builder` для создания сложных планов тренировок с множеством параметров.
*   **New Feature (Integration):** Добавлена функция «Массовое продление» в `AdminService`, которая объединяет универсальный репозиторий, `GenericFilter` с лямбда-выражениями и бизнес-логику для продления абонементов участников, срок действия которых скоро истекает.

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
    *   **Generic Repository:** `Repository<T>` для стандартизированного доступа к данным.
