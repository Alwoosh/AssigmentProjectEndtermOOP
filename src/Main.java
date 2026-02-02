package edu.aitu.oop3;

import edu.aitu.oop3.models.*;
import edu.aitu.oop3.patterns.GymConfig;
import edu.aitu.oop3.repositories.*;
import edu.aitu.oop3.services.AdminService;
import edu.aitu.oop3.services.BookingService;
import edu.aitu.oop3.services.NotificationService;
import io.javalin.Javalin;

import java.time.LocalDateTime;
import java.util.Map;

public class Main {
    private static final MemberRepository memberRepo = new PostgresMemberRepository();
    private static final ClassRepository classRepo = new PostgresClassRepository();
    private static final BookingRepository bookingRepo = new PostgresBookingRepository();
    private static final NotificationService notificationService = (member, fitnessClass) ->
            System.out.println("[Notification] Dear " + member.getName() +
                    ", you are booked for '" + fitnessClass.getTitle() + "'!");

    private static final BookingService bookingService = new BookingService(memberRepo, classRepo, bookingRepo, notificationService);
    private static final AdminService adminService = new AdminService(classRepo, memberRepo);

    public static void main(String[] args) {
        int port = Integer.parseInt(System.getenv().getOrDefault("PORT", "8080"));
        var app = Javalin.create(config -> {
            config.router.treatMultipleSlashesAsSingleSlash = true;
        }).start(port);

        app.get("/api/classes", ctx -> ctx.json(classRepo.findAll()));

        app.get("/api/members/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                Member member = memberRepo.findById(id);
                if (member != null) {
                    ctx.json(member);
                } else {
                    ctx.status(404).result("Member not found");
                }
            } catch (NumberFormatException e) {
                ctx.status(400).result("Invalid ID");
            }
        });

        app.post("/api/book", ctx -> {
            try {
                Map<String, Object> body = ctx.bodyAsClass(Map.class);
                int memberId = Integer.parseInt(body.get("memberId").toString());
                int classId = Integer.parseInt(body.get("classId").toString());

                bookingService.bookClass(memberId, classId);
                ctx.status(201).result("Booking successful!");
            } catch (Exception e) {
                ctx.status(400).result(e.getMessage());
            }
        });

        app.before("/api/admin/*", ctx -> {
            String password = ctx.header("X-Admin-Password");
            if (!GymConfig.getInstance().getAdminPassword().equals(password)) {
                ctx.status(401).result("Access Error: Invalid admin password");
            }
        });

        app.get("/api/admin/members", ctx -> ctx.json(memberRepo.findAll()));

        app.post("/api/admin/classes", ctx -> {
            try {
                Map<String, Object> body = ctx.bodyAsClass(Map.class);
                String title = body.get("title").toString();
                String instructor = body.get("instructor").toString();
                int capacity = Integer.parseInt(body.get("capacity").toString());
                LocalDateTime time = LocalDateTime.parse(body.get("time").toString());

                adminService.addClass(title, instructor, capacity, time);
                ctx.status(201).result("Class added!");
            } catch (Exception e) {
                ctx.status(400).result("Error adding class: " + e.getMessage());
            }
        });

        app.delete("/api/admin/classes/{id}", ctx -> {
            try {
                int id = Integer.parseInt(ctx.pathParam("id"));
                adminService.removeClass(id);
                ctx.result("Class deletion request processed");
            } catch (NumberFormatException e) {
                ctx.status(400).result("Invalid ID");
            }
        });

        System.out.println("Server started on port: " + port);
    }
}
