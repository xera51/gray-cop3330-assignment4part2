Notes
<p>I added PopUps to warn users if they had yet to save a change. This ended up creating buggy behaviour, and after failing to figure out a fix,
  I attempted to rollback the directory, but was unsuccessful. Opening a list when another list is already open causes undefined behaviour</p>
<p> Help is located in Help->Getting Started</p>

<p>EDIT: Figured out how to rollback, rolled back to the commit "Test" about 2 hours before the due date</p>
<p>While making sure the behaviour was corrected, I ended up generating more errors on accident</p>
<p>I think all the issues are coming form my custom ListCell.
Turns out it is horribly broken when it comes to making cells update and delete properly.
I had issues creating it and kind of used a work-around solution, so I guess it is to be expected</p>
<p>Shouldn't be a problem unless you open a bunch of different lists of different sizes and lengths.
Core behaviours should all work</p>