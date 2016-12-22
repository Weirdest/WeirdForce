#Welcome To The WeirdForce Mod for 1.7.10!
<br>
###If you already have the compiled `*.jar` file & don't know what to do with it:
####For Windows users:
Go to your `.minecraft` folder by opening explorer & typing in `%appdata%`, from there you will the `.minecraft` folder. Inside the `.minecraft` folder should be a `mods` folder, if not then make one. Simply place your `*.jar` file in the folder and your good to go!

####For Linux Users:
If your like me and use linux, you know that some things can get a little funky. One of things that some people don't know is on linux (\*nix really) when you start the name of a folder with a period it will become "hidden". Assuming that you left things as the defaults your <b>`.minecraft`</b> folder should be inside your `home` directory. If you don't know what that is, it's `/home/[your username here]/`. So therefor your `.minecraft` folder is at: `/home/[your username here]/.minecraft`. Once you have that simply drag the `*.jar` file into the mods folder!
<hr>
###If your want to compile yourself
####For Windows Users:
No idea, never tried it! lol
####For Linux Users:
1. Downlaod the code
  + Using `git`
    - Type `git clone [repo here].git` into the command line and allow git to do its thing. You can get the link from the home page of the mod where it says *"Download or Clone"*
  + Using the `zip` file
    - Download the repo's zip file and move it into its own directory (when you type `ls` the zip file is all you should see). Then type `unzip [insert zip name here]` & it will extract the files into the current working directory.
2. Compiling the code:
  1. Make sure you have a JDK (Java Development Kit) installed properly or you will not be able to compile the mod. You can find out if you have a JDK installed by typing `javac -version` into a command line. The output should be something like `javac 1.8.X_XXX` **Note: I have not tried to compile this mod with anything less than java 1.8**
  2. Now type `./gradlew build` and it should compile the mod for you. The mods `*.jar` file should be inside the new directory `build/libs`
