=========
Selection
=========

.. contents::
    :local:
    :backlinks: none
    :depth: 1


Save all capital letters in every article title
------------------------------

::

    -b example.bib -sc +t article +k title

Loads the bibliography ``example.bib``

The ``-sc`` will load the save capitals command

The ``+t article`` parameter will only select entries of typ article

The ``+k title`` parameter will only select title keys

.. topic:: Example: Enclosing capital letters in article titles

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
          title  = {{E}vents in the {S}emantics of {E}nglish},
          year   = {1990},
          month  = {01},
          place  = {Cambridge, MA},
        }
