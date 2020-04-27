package data.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class ServiceArea {
    @Id
    private int AreaId;

    private String AreaName;

    public int getAreaId() {
        return AreaId;
    }

    public void setAreaId(int areaId) {
        AreaId = areaId;
    }

    public String getAreaName() {
        return AreaName;
    }

    public void setAreaName(String areaName) {
        AreaName = areaName;
    }
}
