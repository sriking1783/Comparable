package ParkingLot;

class ParkingLot{
    ParkingSpace[] spaces;

    public ParkingSpace get_empty_space(){
        for(ParkingSpace space: spaces){
            if(space.is_empty)
                return space;
        }
        return null;
    }

    public void make_space_empty(ParkingSpace space) {
        space.is_empty = true;
    }

    public void make_space_occupied(ParkingSpace space) {
        space.is_empty = false;
    }


}
