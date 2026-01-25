package edu.aitu.oop3.models;

public class TrainingPlan {
    private String goal;
    private int daysPerWeek;
    private boolean includeCardio;
    private String personalTrainer;

    private TrainingPlan(Builder builder) {
        this.goal = builder.goal;
        this.daysPerWeek = builder.daysPerWeek;
        this.includeCardio = builder.includeCardio;
        this.personalTrainer = builder.personalTrainer;
    }

    public String getGoal() { return goal; }
    public int getDaysPerWeek() { return daysPerWeek; }
    public boolean isIncludeCardio() { return includeCardio; }
    public String getPersonalTrainer() { return personalTrainer; }

    @Override
    public String toString() {
        return "TrainingPlan{" +
                "goal='" + goal + '\'' +
                ", daysPerWeek=" + daysPerWeek +
                ", includeCardio=" + includeCardio +
                ", personalTrainer='" + personalTrainer + '\'' +
                '}';
    }

    public static class Builder {
        private String goal;
        private int daysPerWeek;
        private boolean includeCardio;
        private String personalTrainer;

        public Builder setGoal(String goal) { this.goal = goal; return this; }
        public Builder setDaysPerWeek(int days) { this.daysPerWeek = days; return this; }
        public Builder setCardio(boolean include) { this.includeCardio = include; return this; }
        public Builder setTrainer(String name) { this.personalTrainer = name; return this; }

        public TrainingPlan build() {
            return new TrainingPlan(this);
        }
    }
}
