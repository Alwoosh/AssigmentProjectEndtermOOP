package edu.aitu.oop3.services;

import edu.aitu.oop3.exceptions.BookingAlreadyExistsException;
import edu.aitu.oop3.exceptions.ClassFullException;
import edu.aitu.oop3.exceptions.MembershipExpiredException;
import edu.aitu.oop3.models.FitnessClass;
import edu.aitu.oop3.models.Member;
import edu.aitu.oop3.repositories.BookingRepository;
import edu.aitu.oop3.repositories.ClassRepository;
import edu.aitu.oop3.repositories.MemberRepository;

import java.time.LocalDate;

public class BookingService {
    private final MemberRepository memberRepo;
    private final ClassRepository classRepo;
    private final BookingRepository bookingRepo;
    private final NotificationService notificationService;

    public BookingService(MemberRepository memberRepo,
                          ClassRepository classRepo,
                          BookingRepository bookingRepo,
                          NotificationService notificationService) {
        this.memberRepo = memberRepo;
        this.classRepo = classRepo;
        this.bookingRepo = bookingRepo;
        this.notificationService = notificationService;
    }

    public void bookClass(int memberId, int classId)
            throws MembershipExpiredException, ClassFullException, BookingAlreadyExistsException {

        // 1. Получаем данные
        Member member = memberRepo.findById(memberId);
        FitnessClass fitnessClass = classRepo.findById(classId);

        if (member == null) {
            throw new IllegalArgumentException("Участник не найден");
        }
        if (fitnessClass == null) {
            throw new IllegalArgumentException("Занятие не найдено");
        }

        // 2. Проверка: Активен ли абонемент?
        if (member.getExpiryDate().isBefore(LocalDate.now())) {
            throw new MembershipExpiredException("Ваш абонемент истек!");
        }

        // 3. Проверка: Есть ли место в классе?
        int currentCount = classRepo.getCurrentBookingCount(classId);
        if (currentCount >= fitnessClass.getMaxCapacity()) {
            throw new ClassFullException("В классе нет свободных мест.");
        }

        // 4. Проверка: Уже записан?
        if (bookingRepo.exists(memberId, classId)) {
            throw new BookingAlreadyExistsException("Вы уже записаны на это занятие.");
        }

        // 5. Сохранение и Уведомление
        bookingRepo.save(memberId, classId);
        notificationService.sendBookingConfirmation(member, fitnessClass);
        System.out.println("Бронирование успешно завершено!");
    }
}
