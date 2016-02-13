package cretion.core.component.common;

import cretion.core.component.Component;
import cretion.data.ProfileData;

public class ExperienceComponent extends Component {
    private ProfileData profileData;

    public ExperienceComponent(ProfileData _profileData) {
        profileData = _profileData;
    }

    public int getLevel() {
        return profileData.getLevelData();
    }

    public int getExperience() {
        return profileData.getExperienceData();
    }

    public void gain(int _experience) {
        profileData.setExperience((profileData.getExperienceData() + _experience));
        if (profileData.getExperienceData() >= 100) {
            profileData.setLevel(profileData.getLevelData() + 1);
            profileData.setExperience(0);
        }
    }
}
