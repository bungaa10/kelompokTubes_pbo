package model;

public class QueueItem {
    private int id;
    private String petName;
    private String petType;
    private String status;

    public QueueItem(int id, String petName, String petType, String status) {
        this.id = id;
        this.petName = petName;
        this.petType = petType;
        this.status = status;
    }

    public int getId() { 
        return id; 
    }
    public String getPetName() { 
        return petName; 
    }
    public String getPetType() { 
        return petType; 
    }
    public String getStatus() { 
        return status; 
    }

    
}
