Gnieh Pretty Printer
====================

Pretty Printer library based on the well-known Philip Wadler's "Prettier Printer".

The API was inspired by the Haskell [`Text.PrettyPrint.Leijen`](http://hackage.haskell.org/packages/archive/wl-pprint/1.0/doc/html/Text-PrettyPrint-Leijen.html) module.

Installation
------------

`gnieh-pp` is published in the Maven Central Repository, so you can simply add it to your sbt project by using:
```scala
"org.gnieh" %% "gnieh-pp" % "0.1"
```
It is currently compiled for Scala 2.9.2 and 2.10

If you are using maven with Scala 2.9.*, add this dependency:
```xml
<dependency>
  <groupId>org.gnieh</groupId>
  <artifactId>gnieh-pp_2.9.2</artifactId>
  <version>0.1</version>
  <type>pom</type>
</dependency>
```

If you are using maven with Scala 2.10.*, add this dependency:
```xml
<dependency>
  <groupId>org.gnieh</groupId>
  <artifactId>gnieh-pp_2.10</artifactId>
  <version>0.1</version>
  <type>pom</type>
</dependency>
```

Example
-------

Let's say you want to render some Scala code, formatted in several ways. For example your application generates Scala code and you want to output it so that human beings can read it easily, or sometimes so that it is as concise as possible.

After building your code document, you end up with this:
```scala
val myDoc = "def" :+: "invert" :: group("(" :: align("toInvert:" :+: "Int," :|:
  "really:" :+: "Boolean" :: ")")) :+: nest(2)("=" :|:
    group(nest(2)("if" :+: "(really)" :|: "toInvert") :|:
    nest(2)("else" :|: "-toInvert")))
```

you can render it on 80 columns with proper indentation, which will produce the following output:
```
def invert(toInvert: Int, really: Boolean) =
  if (really) toInvert else -toInvert
```

but you can decide to render it on 10 columns still with proper indentation, which produces:
```
def invert(toInvert: Int,
           really: Boolean) =
  if (really)
    toInvert
  else
    -toInvert
```

Nice, isnt't it?

another option is to discard indentation and groups, which produces some valid Scala code, yet harder to read for people:
```
def invert(toInvert: Int,
really: Boolean) =
if (really)
toInvert
else
-toInvert
```

Getting Started
---------------

So, what do you need to create and render documents?

First, of all, make the following import
```scala
import gnieh.pp._
```

It gives you access to all constructors and userful implicit conversions, as well as to the renderers

### Constructors

There are some base constructors:
 - `empty`is the empty document, left and right unit for all operators,
 - `line` is a new line that can be discarded by a group. In this case it acts as `space`,
 - `space` is the single space document,
 - `linebreak` is a new line that can be discarded by a group. In this case it acts as `empty`,
 - `softline` acts as `space` as long as the result fits in the page, otherwise acts as `line`,
 - `softbreak` acts as `empty` as long as the result fits in the page, otherwise acts as `line`,
 - `text("some text")` creates a text document. The document should never contain new line characters otherwise rendering is more than undeterministic,
 - `string("some text\nwith two lines")` creates a document that is the concatenantion of the two lines with the `line` document, it is much better than the first constructor if your string contains new lines,
 - `word("some text\nwith spaces and line characters"` is even better and splits your string into a list of text documents, each of which contains a single word. Using then `fillSep(words)` you will end up with a document containing all words separated by a `softline`,
 - `group(doc)` discards the new lines in `doc` as long as the result fits in the page, otherwise `doc` acts as if there were no `group`,
 - `nest(indent)(doc)` add `indent` spaces at the beginning of each new line in `doc`,
 - see scaladoc for other constructors...

Of course one can add his own constructors, which are expressed in terms of other constructors and operators

### Operators

There are then 5 primary operators:
 - `doc1 :: doc2` concatenates both documents
 - `doc1 :|: doc2` is equivalent to `doc1 :: line :: doc2`
 - `doc1 :||: doc2` is equivalent to `doc1 :: linebreak :: doc2`
 - `doc1 :\: doc2` is equivalent to `doc1 :: softline :: doc2`
 - `doc1 :\\: doc2` is equivalent to `doc1 :: softbreak :: doc2`

### Renderers

There are 3 default renderers whose job is to format your document following some criterion:
 - `new PrettyRenderer(width)` is a pretty-printer with the column width set to `width`,
 - `CompactRenderer` discards all groups and indentation,
 - `new TruncateRenderer(max, unit, innerRenderer)` is a renderer that truncates the document after rendering by `innerRenderer` after the `max` is reached. `max` can be in
   - lines
   - words
   - non-space characters

Renderers are simply functions from `Doc` to `SimpleDoc`. A `SimpleDoc` is the rendered form of a document which only consist in text and new lines.

Documentation
-------------

You can find the API documentation and all operators on document in the [generated scaladoc](http://gnieh.github.com/gnieh-pp/api/#package)
