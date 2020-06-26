package me.ljseokd.studyjunit5;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class StudyTest {

    @Test
    void create() {
         Study study = new Study();
         assertNotNull(study);
        System.out.println("create");
    }

    @Test
    @Disabled // 테스트 제외
    void create1()  {
        Study study = new Study();
        assertNotNull(study);
        System.out.println("create1");
    }

    @BeforeAll // 시작시 한번 실행
    static void beforeAll(){
        System.out.println("before all");
    }

    @AfterAll // 마지막 한번 실행
    static void afterAll(){
        System.out.println("after all");
    }

    @BeforeEach // 매 테스트 시작 시 실행
    void beforeEach(){
        System.out.println("Before each");
    }

    @AfterEach // 매 테스트 끝날 시 실행
    void afterEach(){
        System.out.println("After each");
    }

}