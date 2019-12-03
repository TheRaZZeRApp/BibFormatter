=========
Examples
=========

.. contents::
    :local:
    :backlinks: none
    :depth: 1


Save every capital E in every article title
------------------------------

::

    -b example.bib -sc +t article +k title +m E

Loads the bibliography ``example.bib``

The ``-sc`` will load the save capitals command

The ``+t article`` parameter will only select entries of typ article

The ``+k title`` parameter will only select title keys

The ``+m E`` parameter will only select capital E letters

.. topic:: Example: Enclosing capital E in article titles

    Before::

        @Article{parsons:1990,
          author = {Parsons, Terence},
          title  = {Events in the Semantics of English},
          year   = {1990},
          month  = {01},
          place  = {Cambridge, MA},
        }

    After::

        @Article{parsons:1990,
          author = {Parsons, Terence},
          title  = {{E}vents in the Semantics of {E}nglish},
          year   = {1990},
          month  = {01},
          place  = {Cambridge, MA},
        }

Remove every key except author title year pages publisher month place
------------------------------

::

    -b example.bib -re +k author title year pages publisher month place +v y

Loads the bibliography ``example.bib``

The ``-re`` parameter will load the remove entry command

The ``+k author title year pages publisher month place`` parameter will only select keys of typ title year pages publisher month place

The ``+v y`` parameter will invert the key selection, meaning that only title year pages publisher month place will not be removed

.. topic:: Example: Remove every key except author title year pages publisher month place

    Before::

        @Article{vanderDoes1991,
          author  = {van der Does, Jaap},
          title   = {A generalized quantifier logic for naked infinitives},
          journal = {Linguistics and Philosophy},
          year    = {1991},
          volume  = {14},
          number  = {3},
          pages   = {241--294},
          month   = {Jun},
          issn    = {1573-0549},
          day     = {01},
          doi     = {10.1007/BF00627404},
          url     = {https://doi.org/10.1007/BF00627404},
        }

        @Book{kroch1974semantics,
          title     = {The Semantics of Scope in English},
          publisher = {Massachusetts Institute of Technology, Department of Foreign Literatures and Linguistics},
          year      = {1974},
          author    = {Kroch, Anthony S.},
          series    = {MIT working papers in linguistics},
          url       = {https://books.google.de/books?id=FcsvvwEACAAJ},
        }

    After::

        @Article{vanderDoes1991,
          author  = {van der Does, Jaap},
          title   = {A generalized quantifier logic for naked infinitives},
          year    = {1991},
          pages   = {241--294},
          month   = {Jun},
        }

        @Book{kroch1974semantics,
          title     = {The Semantics of Scope in English},
          publisher = {Massachusetts Institute of Technology, Department of Foreign Literatures and Linguistics},
          year      = {1974},
          author    = {Kroch, Anthony S.},
        }