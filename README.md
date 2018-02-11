# XSofiaTweaker
### Will most probably be renamed to XSofiaTweaker or XSofiaModder (or something like that)
This xposed module:
* captures the hardware keys from the Joying unit and allows you to reprogram them. This must/can be configured from the Settings (sub)screen.
* allows you to switch on/off the nokill.
* allows you to unmute channel 4, which is used for Google voice feedback.
* can act on the ACC_ON, ACC_OFF and Resume events.

This module can replace the custom key mod mod from Gustden.

# Experimental !!

**Working:** 
* ACC_ON/ACC_OF (key 97 and 98)
* Wake_up/resume (key 99)
* NAVI (key 9)
* Phone/BT(key 27) (confirmed by gtx(aspec))
* BAND (radio) (key 34)
* DVD (key 31) (confirmed by gtx(aspec))
* Eject (key 32) (confirmed by gtx(aspec))
* MEDIA (key 33)
* SRC/Mode (key 37) (confirmed by gtx(aspec))
* HOME (key 3)
* Settings page => page works and settings are used.


**Works with has strange side effects:**
* EQ(ualizer) button: hardware keys disabled for 1-2 minutes, unit mutes and 1-2 seconds later unmutes andhardware keys work again.

**ToDo list:**

* BACK (key 4)
* Dimmer key
* ....


## Some preliminary images
**Main settings screen**
![Image of main settings screen](https://github.com/hvdwolf/XSofiaTweaker/blob/settings/images/01-Settings-Main.png)
**Miscellaneous Settings**
![Image of Miscellaneous Settings](https://github.com/hvdwolf/XSofiaTweaker/blob/settings/images/02-Settings-Miscellaneous.png)

**Application Key Mod Settings**
![Image of app key mods settings](https://github.com/hvdwolf/XSofiaTweaker/blob/settings/images/03-Settings-AppKeyMods.png)
**Call Method**
![Image of app key call method](https://github.com/hvdwolf/XSofiaTweaker/blob/settings/images/03-01-Settings-AppKeyMods.png)
**String to be used**
![Image of app key mods text string](https://github.com/hvdwolf/XSofiaTweaker/blob/settings/images/03-02-Settings-AppKeyMods.png)

**What to do when the contact/unit is switched ON or OFF**
![Image of acc_on_off_resume text string](https://github.com/hvdwolf/XSofiaTweaker/blob/settings/images/Settings-acc_on_of.png)

**System Key Mod Settings**
![Image of System Key Mod Settings](https://github.com/hvdwolf/XSofiaTweaker/blob/settings/images/04-Settings-SysKeyMods.png)
