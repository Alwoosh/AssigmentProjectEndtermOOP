package edu.aitu.oop3.repositories;

import edu.aitu.oop3.models.Member;

public interface MemberRepository {
    Member findById(int id);
    void update(Member member);
}
