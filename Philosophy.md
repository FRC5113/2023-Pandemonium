# Philosophy of the 5113's Robot Code
> Author: Vlad Bondar
## Simply
1. **It Works**
2. It's Clean
3. It's Efficient
4. It's Concise
5. It's simple

In that order

## Naming
1. Variables are in camel case: `autoProgramsChooser`, `teleopInit`
2. Except for constants, which **must** be `final` and `static` are in screaming snake case: `LEFT_PARENT_MOTOR_ID`
3. Class names must make clear what it does

| Class Name | Abbreviation | Explanation                                 |
|------------|--------------|---------------------------------------------|
| Auto       | A_           | Commands to be executed in Auto             |
| Default    | D_           | Commands running by default                 |
| Command    | C_           | Generic Commands (don't fit other category) |
| PID        | P_           | Commands the implement PIDs                 |
| Subsystem  | S_           | Physical descriptor in subsystem form       |

_to be completed_
