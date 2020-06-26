package me.ljseokd.studyjunit5;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ParameterContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.aggregator.AggregateWith;
import org.junit.jupiter.params.aggregator.ArgumentsAccessor;
import org.junit.jupiter.params.aggregator.ArgumentsAggregationException;
import org.junit.jupiter.params.aggregator.ArgumentsAggregator;
import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;
import org.junit.jupiter.params.provider.*;

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


    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(strings = {"날씨가", "많이", "추워지고", "있네요"})
//    @EmptySource
//    @NullSource
    @NullAndEmptySource
    void parameterizedTest2(String message){
        System.out.println(message);
    }

    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(ints = {10, 20, 40})
    void parameterizedTest3(Integer limit){
        System.out.println(limit);
    }

    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @ValueSource(ints = {10, 20, 40})
    void parameterizedTest3_1(@ConvertWith(StudyConverter.class) Study study){
        System.out.println(study.getLimit());
    }

    static class StudyConverter extends SimpleArgumentConverter {
        @Override
        protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
            assertEquals(Study.class,targetType, "Can only convert to Study");
            return new Study(Integer.parseInt(source.toString()));
        }
    }

    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @CsvSource({"10, '자바 스터디'", "20, 스프링"})
    void parameterizedTest4(Integer limit, String name){
        Study study = new Study(limit, name);
        System.out.println(study);
    }

    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @CsvSource({"10, '자바 스터디'", "20, 스프링"})
    void parameterizedTest5(ArgumentsAccessor argumentsAccessor){
        Study study = new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        System.out.println(study);
    }

    @DisplayName("스터디 만들기")
    @ParameterizedTest(name = "{index} {displayName} message={0}")
    @CsvSource({"10, '자바 스터디'", "20, 스프링"})
    void parameterizedTest6(@AggregateWith(StudyAggregator.class) Study study){
        System.out.println(study);
    }

    static class StudyAggregator implements ArgumentsAggregator{
        @Override
        public Object aggregateArguments(ArgumentsAccessor argumentsAccessor, ParameterContext parameterContext) throws ArgumentsAggregationException {
            return new Study(argumentsAccessor.getInteger(0), argumentsAccessor.getString(1));
        }
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