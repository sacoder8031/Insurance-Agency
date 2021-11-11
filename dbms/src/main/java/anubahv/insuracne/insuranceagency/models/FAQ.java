package anubahv.insuracne.insuranceagency.models;

public class FAQ {
    private int id;
    private String question;
    private String answer;
    private String onTopic;

    public FAQ() {
    }

    public FAQ(int id, String question, String answer, String onTopic) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.onTopic = onTopic;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getOnTopic() {
        return onTopic;
    }

    public void setOnTopic(String onTopic) {
        this.onTopic = onTopic;
    }
}
