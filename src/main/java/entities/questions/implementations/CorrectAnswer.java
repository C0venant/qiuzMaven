package entities.questions.implementations;

public class CorrectAnswer{
    private String answer;
    private String explanation;

    public CorrectAnswer(String answer, String explanation) {
        this.answer = answer;
        this.explanation =explanation;
    }

    public String getAnswer() {
        return answer;
    }

    public String getExplanation() {
        return explanation;
    }

    @Override
    public String toString() {
        String res = "answer = " + answer + ", explanation = " + explanation;
        return res;
    }
}
