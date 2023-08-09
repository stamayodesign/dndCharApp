# RPG Dice Roller - Kotlin Project
## Introduction
This project was made in Android Studio from April 2022 - May 2022. The application allows for simulated dice rolling ranging from multiples of the same die, multiples of different dies, and custom dice inputs. The application also allows the addition of modifiers before OR after the dice roll.

My inspiration for this project was that I wanted to add Kotlin to my toolbelt as a software developer and my hobbies of various TableTop Role Playing Games (TTRPGs) including Dungeons & Dragons, Ironsworn, and GURPs. 

# How to use App

<img src="images/dndCharApp_image0-landingpage.jpg" width = "250"/>

The app is divided into four main parts:<br/>
A. [Tabs](#pt1)<br/>
B. [Display](#pt2)<br/>
C. [Dice Buttons](#pt3)<br/>
D. [Character Stats](#pt4)<br/>

## <a name="pt1"></a>(A) Tabs
<img src="images/dndCharApp_image1-tabs.jpg" width = "500"/>

The app has three modes. By default, the app opens in "SINGLE DICE" mode. The current mode's tab button will be red when in use. Click one of the following to jump to a particular mode to learn more:

1. [Single Dice Tab](#sdt)<br/>
2. [Multi Dice Tab](#mdt)<br/>
3. [Custom Dice Tab](#cdt)<br/>

## <a name="pt2"></a>(B) Display

When rolling dice, text will appear to say what you are rolling. The total of the roll plus any modifiers will appear centered below this. You can also toggle "Show Individual Die Rolls" to see what each die rolls separately.

## <a name="pt3"></a>(C) Dice Buttons

This section's UI changes depending on which tab/mode you are currently displaying. These will be touched upon more in the mode section.

## <a name="pt4"></a>(D) Character Stats

The default stats are in a format that matches a Dungeons and Dragon's character scores. By pressing one of these six scores, the modifier will be added to the roll. This can be toggled before or after the roll is made. 

You can edit the stats by using the "Edit Stats" button:



### <a name="sdt"></a>Single Dice Tab

This tab has buttons for a standard set of TTRPG dice (4-, 6-, 8-, 10-, 12-, 20-, 100-sided dice). Each button in the middle has the text "d" and a number, indicating how many sides that die has. When you press one of these buttons, the app will randomize the results of the roll.

Below these seven buttons is a "Clear All" button. This will clear the results of any dice rolled.

Below the "Clear All" button are two sets of minus and plus buttons. The left one will determine the Number of Dice rolled when clicking one of the seven above buttons.



### <a name="mdt"></a>Multi Dice Tab
### <a name="cdt"></a>Custom Dice Tab
