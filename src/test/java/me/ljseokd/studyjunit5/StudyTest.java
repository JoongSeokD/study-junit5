package me.ljseokd.studyjunit5;

import org.junit.jupiter.api.*;

import java.time.Duration;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @Test
    void create_new_study() {
        Study study = new Study();
        assertNotNull(study);
        // 기대값, 실제값, 메시지
        //assertEquals(StudyStatus.DRAFT, study.getStatus(), "스터디를 처음 만들면 상태값이 "+StudyStatus.DRAFT+"여야 한다.");
        //assertEquals(StudyStatus.DRAFT, study.getStatus(), () -> "스터디를 처음 만들면 상태값이 "+StudyStatus.DRAFT+"여야 한다.");
        //Supplier를 사용하지 않으면 테스트가 성공하든 실패하든 문자열을 연산함
        assertEquals(StudyStatus.DRAFT, study.getStatus(), new Supplier<String>() {
            @Override
            public String get() {
                return "스터디를 처음 만들면 상태값이 "+StudyStatus.DRAFT+" 상태다.";
            }
        });
        assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야 한다.");
        assertNull(null);

        assertAll(
                ()-> assertTrue(study.getLimit() > 0, "스터디 최대 참석 가능 인원은 0보다 커야 한다."),
                ()-> assertNull(new Study())
        );

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> new Study(-10));
        assertEquals("limit은 0보다 커야 합니다.", exception.getMessage());

        assertTimeout(Duration.ofSeconds(10), () -> new Study());
        assertTimeout(Duration.ofMillis(10), () ->{
            new Study();
            Thread.sleep(300);
        } );
        assertTimeoutPreemptively(Duration.ofMillis(10), () ->{
            new Study();
            Thread.sleep(300);
        } );
        // TODO 스레드와 관련된 테스트를 사용하면 예상치 못한 결과가 나올 수 있음
    }

    @Test
    @DisplayName("스터디 만들기")
    void create_new_study_again()  {
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