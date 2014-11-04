This is a build framework for managing common tasks in Windows Build environments. This framework handles the following:

- Version Stamping AssemblyInfo.CS files
- Compiling 
- Managing Unit / Functional Tests (NUNIT)
- Packaging
- Generating a Package Manifest

Each module has a core set of functionality points. The main entry locations for a build environent should be 

Build.proj
Deploy.proj

The above two PROJ files simply wrap an existing msbuild script. This allows you to abstract changes to the build specifics while still maintaining a level of consistency when building multiple projects

