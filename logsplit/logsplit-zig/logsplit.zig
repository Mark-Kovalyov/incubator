const std = @import("std");

pub fn main() !void {
    const _  = std.io.getStdIn().reader();
    const stdout = std.io.getStdOut().writer();
    try stdout.print("Hello, {s}!\n", .{"world"});
}
