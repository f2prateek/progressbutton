ProgressButton
==============

ProgressButton is a custom progress indicator with a tiny footprint. Based on the sample code provided by [Roman Nurik][4] on [Google Code][3].
The default implementation provides a pin progress button as seen on the [Android design site][1].

![Pin Progress Button Android developer][2]

The color indicates whether it's downloaded (blue) or not (gray). The appearance of the pin indicates whether the download is permanent (white, upright) or temporary (gray, diagonal). When state is in the process of changing, progress is indicated by a moving pie chart

Try out the sample application in the samples/ folder of the project.

![Sample application Screenshot][5]

The sample application shows 4 different buttons in each row. The first row is done via xml, and the second row is via code.

1. Default implementation.
2. A progress button that is pinned and NOT clickable by the user.
3. A progress button that starts in a pinned state, and IS clickable by the user.
4. A progress button that is styled. In addition to changing the color, you can supply your resources by :

	* setShadowDrawable in code, shadowDrawable in xml
	* setUnpinnedDrawable in code, unpinnedDrawable in xml
	* setPinnedDrawable in code, pinnedDrawable in xml

> Now, a quick word of caution: consider whether this is really necessary for your app before using it! Ideally, all your content should be available offline, and it should download fast enough that showing progress would be superfluous. That said, if your content make take some time to download and you can't come up with an awesome, automagical smart-offlining scheme, then this may be an appropriate UI element.

Usage
=====

Due the the theming capabilities of the library, it must be referenced as a library project in eclipse. 
The library is also available through maven.

```xml
<dependency>
  <groupId>com.f2prateek</groupId>
  <artifactId>progressbutton</artifactId>
  <version>1.0.0</version>
</dependency>
```

Developed By
============

* Prateek Srivastava - <f2prateek@gmail.com>


License
=======

    Copyright 2013 Prateek Srivastava
    Copyright 2012 Roman Nurik

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.


[1]: https://developer.android.com/design/building-blocks/progress.html#custom-indicators
[2]: https://developer.android.com/design/media/progress_activity_custom.png
[3]: https://code.google.com/p/romannurik-code/source/browse/misc/pinprogress
[4]: https://plus.google.com/+RomanNurik/posts/TbCkqQN4AEk
[5]: https://raw.github.com/f2prateek/progressbutton/master/assets/sample-app-screenshot.png
