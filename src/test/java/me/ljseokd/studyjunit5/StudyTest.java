package me.ljseokd.studyjunit5;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.Duration;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class StudyTest {

    @Test
    @Disabled
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
    @Disabled
    void time_out_test() throws Exception {
        assertTimeoutPreemptively(Duration.ofMillis(10), () ->{
            new Study();
            Thread.sleep(3000);
        });
    }
    @Test
    @Disabled
    void 조건에_따른_테스트() {
        assumeTrue("LOCAL".equalsIgnoreCase(System.getenv("TEST_ENV")));
    }


    @CustomTag
    void create_new_study_fast()  {
        Study study = new Study();
        assertNotNull(study);
        System.out.println("create1");
    }


    @DisplayName("스터디 만들기")
    @RepeatedTest(value = 10, name = "{displayName}, {currentRepetition}/{totalRepetitions}")
    void repeatTest(RepetitionInfo repetitionInfo){
        System.out.println("test " + repetitionInfo.getCurrentRepetition() + "/" + repetitionInfo.getTotalRepetitions());
    }

    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(strings = {"날씨가", "많이", "추워지고", "있네요"})
    void parameterizedTest(String message){
        System.out.println(message);
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